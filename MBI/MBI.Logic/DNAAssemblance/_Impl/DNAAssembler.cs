using System.Collections.Generic;
using MBI.Logic.Entities;
using MBI.Logic.Extensions;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class DNAAssembler : IDNAAssembler
	{
		private readonly IScaffoldBuilder _assemblyBuilder;

		public DNAAssembler(IScaffoldBuilder assemblyBuilder)
		{
			_assemblyBuilder = assemblyBuilder;
		}

		public IList<Scaffold> Assemble(Contig[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new List<Scaffold>();

			foreach (var permutation in contigs.GetPermutations().Select(x => x.ToArray()))
			{
				var scaffold = _assemblyBuilder.Build(permutation, pairedEndTags);

				if (scaffold.Rank > 0)
				{
					result.Add(scaffold);
				}
			}

			return result.OrderByDescending(x => x.Rank).ToList();
		}
	}
}
