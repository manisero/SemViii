using System;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ScaffoldValidator : IScaffoldValidator
	{
		public int Validate(string[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = 0;

			foreach (var pet in pairedEndTags)
			{
				var beginningFound = false;
				var totalLength = 0;

				foreach (var contig in contigs)
				{
					if (beginningFound)
					{
						if (contig.Contains(pet.End))
						{
							totalLength += GetPetEndLengthInContig(contig, pet.End);

							if (totalLength <= pet.Length)
							{
								result += pet.Beginning.Length + pet.End.Length;
							}

							continue;
						}
						else
						{
							totalLength += contig.Length;
						}
					}
					else if (contig.Contains(pet.Beginning))
					{
						beginningFound = true;
						totalLength += GetPetBeginningLengthInContig(contig, pet.Beginning);
					}
				}
			}

			return result;
		}

		private int GetPetBeginningLengthInContig(string contig, string petBeginning)
		{
			return contig.Split(new[] { petBeginning }, StringSplitOptions.None).Last().Length + petBeginning.Length;
		}

		private int GetPetEndLengthInContig(string contig, string petEnd)
		{
			return contig.Split(new[] { petEnd }, StringSplitOptions.None).First().Length + petEnd.Length;
		}
	}
}
