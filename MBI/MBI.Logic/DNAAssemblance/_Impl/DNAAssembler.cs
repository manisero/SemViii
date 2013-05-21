using System.Collections.Generic;
using MBI.Logic.Entities;
using MBI.Logic.Extensions;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class DNAAssembler : IDNAAssembler
	{
		private readonly IContigsFilter _contigsFilter;
		private readonly IScaffoldBuilder _scaffoldBuilder;

		public DNAAssembler(IContigsFilter contigsFilter, IScaffoldBuilder scaffoldBuilder)
		{
			_contigsFilter = contigsFilter;
			_scaffoldBuilder = scaffoldBuilder;
		}

		public IList<Scaffold> Assemble(Contig[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new List<Scaffold>();

			var contigsPermutations = contigs.GetPermutations();

			foreach (var permutation in _contigsFilter.Filter(contigsPermutations, pairedEndTags))
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
