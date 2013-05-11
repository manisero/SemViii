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
							totalLength += GetPetEndLengthInContig(piece, pet.End);

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
						totalLength += GetPetBeginningLengthInContig(piece, pet.Beginning);
					}
				}

				if (!beginningFound && contigs.First().Content.Contains(pet.End))
				{
					result.Rank += pet.End.Length;
				}
				else if (!endFound && contigs.Last().Content.Contains(pet.Beginning))
				{
					result.Rank += pet.Beginning.Length;
				}
			}

			return result;
		}

		private int GetPetBeginningLengthInContig(ScaffoldPiece piece, string petBeginning)
		{
			return piece.Content.Split(new[] { petBeginning }, StringSplitOptions.None).Last().Length + petBeginning.Length;
		}

		private int GetPetEndLengthInContig(ScaffoldPiece piece, string petEnd)
		{
			return piece.Content.Split(new[] { petEnd }, StringSplitOptions.None).First().Length + petEnd.Length;
		}
	}
}
