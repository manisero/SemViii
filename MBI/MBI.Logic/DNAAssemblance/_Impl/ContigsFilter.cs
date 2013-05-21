using System.Collections.Generic;
using MBI.Logic.Entities;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ContigsFilter : IContigsFilter
	{
		public IEnumerable<IList<Contig>> Filter(IList<IList<Contig>> contigsCombinations, IEnumerable<PairedEndTag> pairedEndTags)
		{
			foreach (var pairedEndTag in pairedEndTags)
			{
				var beginning = contigsCombinations[0].FirstOrDefault(x => x.Content.Contains(pairedEndTag.Beginning));

				if (beginning == null)
				{
					continue;
				}

				var end = contigsCombinations[0].FirstOrDefault(x => x.Content.Contains(pairedEndTag.End));

				if (end == null || beginning == end)
				{
					continue;
				}

				var result = new List<IList<Contig>>();

				foreach (var combination in contigsCombinations)
				{
					if (combination.IndexOf(beginning) < combination.IndexOf(end))
					{
						result.Add(combination);
					}
				}

				return result;
			}

			return contigsCombinations;
		}
	}
}