using System.Collections.Generic;
using MBI.Logic.Entities;
using System.Linq;
using MBI.Logic.Extensions;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class ContigsFilter : IContigsFilter
	{
		public IEnumerable<IList<Contig>> Filter(IList<Contig> contigs, IEnumerable<PairedEndTag> pairedEndTags)
		{
			var permutations = contigs.GetPermutations();

			foreach (var pairedEndTag in pairedEndTags)
			{
				var beginning = permutations[0].FirstOrDefault(x => x.Content.Contains(pairedEndTag.Beginning));

				if (beginning == null)
				{
					continue;
				}

				var end = permutations[0].FirstOrDefault(x => x.Content.Contains(pairedEndTag.End));

				if (end == null || beginning == end)
				{
					continue;
				}

				var result = new List<IList<Contig>>();

				foreach (var combination in permutations)
				{
					if (combination.IndexOf(beginning) < combination.IndexOf(end))
					{
						result.Add(combination);
					}
				}

				return result;
			}

			return permutations;
		}
	}
}