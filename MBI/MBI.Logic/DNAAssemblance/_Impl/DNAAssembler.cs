using System.Collections.Generic;
using MBI.Logic.Entities;
using MBI.Logic.Extensions;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class DNAAssembler : IDNAAssembler
	{
		private readonly IAssemblyFilter _assemblyFilter;
		private readonly IScaffoldBuilder _scaffoldBuilder;

		public DNAAssembler(IAssemblyFilter assemblyFilter, IScaffoldBuilder scaffoldBuilder)
		{
			_assemblyFilter = assemblyFilter;
			_scaffoldBuilder = scaffoldBuilder;
		}

		public IList<Scaffold> Assemble(Contig[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new List<Scaffold>();

			var contigsPermutations = contigs.GetPermutations();

			foreach (var permutation in _assemblyFilter.Filter(contigsPermutations, pairedEndTags))
			{
				var scaffold = _scaffoldBuilder.Build(permutation.ToArray(), pairedEndTags);

				if (scaffold.Rank > 0)
				{
					result.Add(scaffold);
				}
			}

			return result.OrderByDescending(x => x.Rank).ToList();
		}
	}
}
