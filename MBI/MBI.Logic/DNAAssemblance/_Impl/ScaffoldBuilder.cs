using System;
using System.Linq;
using MBI.Logic.Entities;
using MBI.Logic.Extensions;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ScaffoldBuilder : IScaffoldBuilder
	{
		private readonly ISettingsProvider _settingsProvider;

		public ScaffoldBuilder(ISettingsProvider settingsProvider)
		{
			_settingsProvider = settingsProvider;
		}

		public Scaffold Build(Contig[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new Scaffold();
			contigs.ForEach(result.Pieces.Add);

			foreach (var pet in pairedEndTags)
			{
				int beginningIndex;
				int beginningRank;
				int petLengthInBeginning;

				if (TryMatchPetBeginning(pet.Beginning, result, out beginningIndex, out beginningRank, out petLengthInBeginning))
				{
					int endRank;
					int petLengthTillEnd;
					var lengthFixed = false;

					if (TryMatchPetEnd(pet.End, result, beginningIndex + 1, out endRank, out petLengthTillEnd, ref lengthFixed))
					{
						var totalLength = petLengthInBeginning + petLengthTillEnd;

						if (lengthFixed ? totalLength == pet.Length : totalLength <= pet.Length)
						{
							result.Pieces.Insert(beginningIndex + 1, new Gap(pet.Length - totalLength));
							result.Rank += beginningRank + endRank;
						}
					}
					else
					{
						result.Rank += RankNotPairedPetPart(contigs, pet);
					}
				}
				else
				{
					result.Rank += RankNotPairedPetPart(contigs, pet);
				}
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

				if (TryPartiallyMatchPetPartIntoContigBeginning(contig, beginning, true, out rank, out length) ||
					TryPartiallyMatchPetPartIntoContigEnd(contig, beginning, true, out rank, out length))
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

		private bool TryMatchPetEnd(string end, Scaffold scaffold, int startIndex, out int rank, out int length, ref bool gapOccured)
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

					if (piece is Gap)
					{
						gapOccured = true;
					}
				}
			}

			totalLength = 0;

			// Try to find partial match
			for (int i = startIndex; i < scaffold.Pieces.Count; i++)
			{
				var piece = scaffold.Pieces[i];
				int lengthInPiece;

				if (piece is Contig && (TryPartiallyMatchPetPartIntoContigBeginning(piece as Contig, end, false, out rank, out lengthInPiece) ||
										TryPartiallyMatchPetPartIntoContigEnd(piece as Contig, end, false, out rank, out lengthInPiece)))
				{
					length = totalLength + lengthInPiece;
					return true;
				}
				else
				{
					totalLength += piece.Length;

					if (piece is Gap)
					{
						gapOccured = true;
					}
				}
			}

			rank = 0;
			length = 0;
			return false;
		}

		private int RankNotPairedPetPart(Contig[] contigs, PairedEndTag pet)
		{
			if (contigs.First().Content.Contains(pet.End))
			{
				return pet.End.Length;
			}
			
			if (contigs.Last().Content.Contains(pet.Beginning))
			{
				return pet.Beginning.Length;
			}

			int rank;
			int length;
			
			if (TryPartiallyMatchPetPartIntoContigBeginning(contigs.First(), pet.End, false, out rank, out length) ||
				TryPartiallyMatchPetPartIntoContigEnd(contigs.First(), pet.End, false, out rank, out length))
			{
				return rank;
			}
			
			if (TryPartiallyMatchPetPartIntoContigBeginning(contigs.Last(), pet.Beginning, true, out rank, out length) ||
				TryPartiallyMatchPetPartIntoContigEnd(contigs.Last(), pet.Beginning, true, out rank, out length))
			{
				return rank;
			}

			return 0;
		}

		private int GetFullPetBeginningLengthInContig(Contig piece, string petBeginning)
		{
			return piece.Content.Split(new[] { petBeginning }, StringSplitOptions.None).Last().Length + petBeginning.Length;
		}

		private int GetFullPetEndLengthInContig(Contig piece, string petEnd)
		{
			return piece.Content.Split(new[] { petEnd }, StringSplitOptions.None).First().Length + petEnd.Length;
		}

		private bool TryPartiallyMatchPetPartIntoContigBeginning(Contig piece, string petPart, bool isPetBeginning, out int rank, out int length)
		{
			var minLength = (int)(_settingsProvider.PartialMatchMinPercentage * petPart.Length) - 1;

			if (minLength < 0)
			{
				minLength = 0;
			}

			for (int subPartLength = petPart.Length - 1; subPartLength != minLength; subPartLength--)
			{
				var subPart = petPart.Substring(petPart.Length - subPartLength);

				if (piece.Content.StartsWith(subPart))
				{
					rank = subPartLength;
					length = isPetBeginning ? piece.Content.Length + (petPart.Length - subPartLength) : subPartLength;
					return true;
				}
			}

			rank = 0;
			length = 0;
			return false;
		}

		private bool TryPartiallyMatchPetPartIntoContigEnd(Contig piece, string petPart, bool isPetBeginning, out int rank, out int length)
		{
			var minLength = (int)(_settingsProvider.PartialMatchMinPercentage * petPart.Length) - 1;

			if (minLength < 0)
			{
				minLength = 0;
			}

			for (int subPartLength = petPart.Length - 1; subPartLength != minLength; subPartLength--)
			{
				var subPart = petPart.Substring(0, subPartLength);

				if (piece.Content.EndsWith(subPart))
				{
					rank = subPartLength;
					length = isPetBeginning ? subPartLength : piece.Content.Length + (petPart.Length - subPartLength);
					return true;
				}
			}

			rank = 0;
			length = 0;
			return false;
		}
	}
}
