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
				var rank = _assemblyBuilder.Build(permutation, pairedEndTags);

				if (rank > 0)
				{
					result.Add(new Scaffold { Pieces = permutation.Cast<ScaffoldPiece>().ToArray(), Rank = rank });
				}
			}

			return result.OrderByDescending(x => x.Rank).ToList();
		}
	}
}
