using System.Collections.Generic;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ScaffoldValidator : IScaffoldValidator
	{
		public int Validate(IEnumerable<string> contigs, IEnumerable<PairedEndTag> pairedEndTags)
		{
			var result = 0;
			var flags = pairedEndTags.ToDictionary(x => x, x => false);

			foreach (var contig in contigs)
			{
				foreach (var tag in pairedEndTags)
				{
					if (contig.Contains(tag.Beginning))
					{
						flags[tag] = true;
					}

					if (flags[tag] && contig.Contains(tag.End))
					{
						result += tag.Beginning.Length + tag.End.Length;
					}
				}
			}

			return result;
		}
	}
}
