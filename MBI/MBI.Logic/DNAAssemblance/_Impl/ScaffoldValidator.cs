using System;
using System.Linq;
using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ScaffoldValidator : IScaffoldValidator
	{
		public int Validate(Contig[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = 0;

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

							break;
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
				}

				if (endFound && totalLength <= pet.Length)
				{
					result += pet.Beginning.Length + pet.End.Length;
				}
				else if (contigs.First().Content.Contains(pet.End))
				{
					result += pet.End.Length;
				}
				else if (contigs.Last().Content.Contains(pet.Beginning))
				{
					result += pet.Beginning.Length;
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
