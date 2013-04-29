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
				var lengthSoFar = 0;

				foreach (var contig in contigs)
				{
					if (beginningFound)
					{
						if (contig.Contains(pet.End))
						{
							lengthSoFar += GetPetEndLengthInContig(contig, pet.End);

							if (lengthSoFar <= pet.Length)
							{
								result += pet.Beginning.Length + pet.End.Length;
							}

							continue;
						}
						else
						{
							lengthSoFar += contig.Length;
						}
					}
					else if (contig.Contains(pet.Beginning))
					{
						beginningFound = true;
						lengthSoFar += GetPetBeginningLengthInContig(contig, pet.Beginning);
					}
				}
			}

			return result;
		}

		private int GetPetBeginningLengthInContig(string contig, string petBeginning)
		{
			return contig.Split(new[] { petBeginning }, 2, StringSplitOptions.None)[1].Length + petBeginning.Length;
		}

		private int GetPetEndLengthInContig(string contig, string petEnd)
		{
			return contig.Split(new[] { petEnd }, 2, StringSplitOptions.None)[0].Length + petEnd.Length;
		}
	}
}
