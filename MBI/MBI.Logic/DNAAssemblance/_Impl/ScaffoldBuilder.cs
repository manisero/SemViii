using System;
using System.Linq;
using MBI.Logic.Entities;
using MBI.Logic.Extensions;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ScaffoldBuilder : IScaffoldBuilder
	{
		public Scaffold Build(Contig[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new Scaffold();
			contigs.ForEach(result.Pieces.Add);

			foreach (var pet in pairedEndTags)
			{
				var beginningFound = false;
				var endFound = false;
				var totalLength = 0;
				var lengthFixed = false;

				for (int i = 0; i < result.Pieces.Count; i++)
				{
					var piece = result.Pieces[i];

					if (beginningFound)
					{
						if (piece.Content.Contains(pet.End))
						{
							endFound = true;
							totalLength += GetFullPetEndLengthInContig(piece, pet.End);

							if (lengthFixed ? totalLength == pet.Length : totalLength <= pet.Length)
							{
								result.Pieces.Insert(i, new Gap(pet.Length - totalLength));
								result.Rank += pet.Beginning.Length + pet.End.Length;
							}

							break;
						}
						else
						{
							totalLength += piece.Length;

							if (piece is Gap)
							{
								lengthFixed = true;
							}
						}
					}
					else if (piece.Content.Contains(pet.Beginning))
					{
						beginningFound = true;
						totalLength += GetFullPetBeginningLengthInContig(piece, pet.Beginning);
					}
				}

				result.Rank += RankNotPairedPetPart(contigs, pet, beginningFound, endFound);
			}

			return result;
		}

		private bool TryMatchPetBeginning(string beginning, Scaffold scaffold, out int index, out int rank, out int length)
		{
			// Try to find full match
			for (int i = 0; i < scaffold.Pieces.Count; i++)
			{
				var contig = scaffold.Pieces[i] as Contig;

				if (contig == null)
				{
					continue;
				}

				if (contig.Content.Contains(beginning))
				{
					index = i;
					rank = beginning.Length;
					length = GetFullPetBeginningLengthInContig(contig, beginning);
					return true;
				}
			}

			// Try to find partial match
			for (int i = 0; i < scaffold.Pieces.Count; i++)
			{
				var contig = scaffold.Pieces[i] as Contig;

				if (contig == null)
				{
					continue;
				}

				if (TryPartiallyMatchPetPartIntoContigBeginning(contig, beginning, out rank, out length) ||
					TryPartiallyMatchPetPartIntoContigEnd(contig, beginning, out rank, out length))
				{
					index = i;
					return true;
				}
			}

			index = 0;
			rank = 0;
			length = 0;
			return false;
		}

		private bool TryMatchPetEnd(string end, Scaffold scaffold, int startIndex, out int rank, out int length)
		{
			// Try to find full match
			var totalLength = 0;

			for (int i = startIndex; i < scaffold.Pieces.Count; i++)
			{
				var piece = scaffold.Pieces[i];

				if (piece is Contig && piece.Content.Contains(end))
				{
					rank = end.Length;
					length = totalLength + GetFullPetEndLengthInContig(piece as Contig, end);
					return true;
				}
				else
				{
					totalLength += piece.Length;
				}
			}

			totalLength = 0;

			// Try to find partial match
			for (int i = startIndex; i < scaffold.Pieces.Count; i++)
			{
				var piece = scaffold.Pieces[i];
				int lengthInPiece;

				if (piece is Contig && (TryPartiallyMatchPetPartIntoContigBeginning(piece as Contig, end, out rank, out lengthInPiece) ||
										TryPartiallyMatchPetPartIntoContigEnd(piece as Contig, end, out rank, out lengthInPiece)))
				{
					length = totalLength + lengthInPiece;
					return true;
				}
				else
				{
					totalLength += piece.Length;
				}
			}

			rank = 0;
			length = 0;
			return false;
		}

		private int RankNotPairedPetPart(Contig[] contigs, PairedEndTag pet, bool beginningFound, bool endFound)
		{
			int rank;
			int length;

			if (!beginningFound && contigs.First().Content.Contains(pet.End))
			{
				return pet.End.Length;
			}
			
			if (!endFound && contigs.Last().Content.Contains(pet.Beginning))
			{
				return pet.Beginning.Length;
			}
			
			if (!beginningFound && (TryPartiallyMatchPetPartIntoContigBeginning(contigs.First(), pet.End, out rank, out length) ||
									TryPartiallyMatchPetPartIntoContigEnd(contigs.First(), pet.End, out rank, out length)))
			{
				return rank;
			}
			
			if (!endFound && (TryPartiallyMatchPetPartIntoContigBeginning(contigs.Last(), pet.Beginning, out rank, out length) ||
							  TryPartiallyMatchPetPartIntoContigEnd(contigs.Last(), pet.Beginning, out rank, out length)))
			{
				return rank;
			}

			return 0;
		}

		private int GetFullPetBeginningLengthInContig(ScaffoldPiece piece, string petBeginning)
		{
			return piece.Content.Split(new[] { petBeginning }, StringSplitOptions.None).Last().Length + petBeginning.Length;
		}

		private int GetFullPetEndLengthInContig(ScaffoldPiece piece, string petEnd)
		{
			return piece.Content.Split(new[] { petEnd }, StringSplitOptions.None).First().Length + petEnd.Length;
		}

		private bool TryPartiallyMatchPetPartIntoContigEnd(Contig piece, string petPart, out int rank, out int length)
		{
			for (int subPartLength = petPart.Length - 1; subPartLength != 0; subPartLength--)
			{
				var subPart = petPart.Substring(0, subPartLength);

				if (piece.Content.EndsWith(subPart))
				{
					rank = subPartLength;
					length = subPartLength;
					return true;
				}
			}

			rank = 0;
			length = 0;
			return false;
		}

		private bool TryPartiallyMatchPetPartIntoContigBeginning(Contig piece, string petPart, out int rank, out int length)
		{
			for (int subPartLength = petPart.Length - 1; subPartLength != 0; subPartLength--)
			{
				var subPart = petPart.Substring(petPart.Length - subPartLength);

				if (piece.Content.StartsWith(subPart))
				{
					rank = subPartLength;
					length = piece.Content.Length;
					return true;
				}
			}

			rank = 0;
			length = 0;
			return false;
		}
	}
}
