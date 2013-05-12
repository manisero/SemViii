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
							totalLength += GetFullPetEndLengthInPiece(piece, pet.End);

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
						totalLength += GetFullPetBeginningLengthInPiece(piece, pet.Beginning);
					}
				}

				int rank;
				int length;

				if (!beginningFound && contigs.First().Content.Contains(pet.End))
				{
					result.Rank += pet.End.Length;
				}
				else if (!endFound && contigs.Last().Content.Contains(pet.Beginning))
				{
					result.Rank += pet.Beginning.Length;
				}
				else if (!beginningFound && (TryPartiallyMatchPetPartIntoContigBeginning(contigs.First(), pet.End, out rank, out length) || 
											 TryPartiallyMatchPetPartIntoContigEnd(contigs.First(), pet.End, out rank, out length)))
				{
					result.Rank += rank;
				}
				else if (!endFound && (TryPartiallyMatchPetPartIntoContigBeginning(contigs.Last(), pet.Beginning, out rank, out length) ||
									   TryPartiallyMatchPetPartIntoContigEnd(contigs.Last(), pet.Beginning, out rank, out length)))
				{
					result.Rank += rank;
				}
			}

			return result;
		}

		private int GetFullPetBeginningLengthInPiece(ScaffoldPiece piece, string petBeginning)
		{
			return piece.Content.Split(new[] { petBeginning }, StringSplitOptions.None).Last().Length + petBeginning.Length;
		}

		private int GetFullPetEndLengthInPiece(ScaffoldPiece piece, string petEnd)
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
