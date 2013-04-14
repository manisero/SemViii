using System.Collections.Generic;
using MBI.Logic.Extensions;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class DNAAssembler : IDNAAssembler
	{
		private readonly IAssemblyValidator _assemblyValidator;

		public DNAAssembler(IAssemblyValidator assemblyValidator)
		{
			_assemblyValidator = assemblyValidator;
		}

		public IList<DNAAssembly> Assemble(string[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new List<DNAAssembly>();

			foreach (var permutation in contigs.GetPermutations())
			{
				var rank = _assemblyValidator.Validate(permutation, pairedEndTags);

				if (rank > 0)
				{
					result.Add(new DNAAssembly { Contigs = permutation.ToArray(), Rank = rank });
				}
			}

			return result.OrderByDescending(x => x.Rank).ToList();
		}
	}
}
