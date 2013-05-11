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

			foreach (var pet in pairedEndTags)
			{
				var beginningFound = false;
				var endFound = false;
				var totalLength = 0;

				foreach (var contig in contigs)
				{
					if (beginningFound)
					{
						if (contig.Content.Contains(pet.End))
						{
							totalLength += GetPetEndLengthInContig(contig, pet.End);
							endFound = true;

							if (totalLength <= pet.Length)
							{
								result.Pieces.Add(new Gap(pet.Length - totalLength));
								result.Rank += pet.Beginning.Length + pet.End.Length;
							}
						}
						else
						{
							totalLength += contig.Content.Length;
						}
					}
					else if (contig.Content.Contains(pet.Beginning))
					{
						beginningFound = true;
						totalLength += GetPetBeginningLengthInContig(contig, pet.Beginning);
					}

					result.Pieces.Add(contig);

					if (endFound)
					{
						break;
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

		private int GetPetBeginningLengthInContig(Contig contig, string petBeginning)
		{
			return contig.Content.Split(new[] { petBeginning }, StringSplitOptions.None).Last().Length + petBeginning.Length;
		}

		private int GetPetEndLengthInContig(Contig contig, string petEnd)
		{
			return contig.Content.Split(new[] { petEnd }, StringSplitOptions.None).First().Length + petEnd.Length;
		}
	}
}
