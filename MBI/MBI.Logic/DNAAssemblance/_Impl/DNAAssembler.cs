﻿using System.Collections.Generic;
using MBI.Logic.Entities;
using MBI.Logic.Extensions;
using System.Linq;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class DNAAssembler : IDNAAssembler
	{
		private readonly IScaffoldValidator _assemblyValidator;

		public DNAAssembler(IScaffoldValidator assemblyValidator)
		{
			_assemblyValidator = assemblyValidator;
		}

		public IList<Scaffold> Assemble(string[] contigs, PairedEndTag[] pairedEndTags)
		{
			var result = new List<Scaffold>();

			foreach (var permutation in contigs.GetPermutations().Select(x => x.ToArray()))
			{
				var rank = _assemblyValidator.Validate(permutation, pairedEndTags);

				if (rank > 0)
				{
					result.Add(new Scaffold { Pieces = permutation.Select(x => new Contig(x)).Cast<ScaffoldPiece>().ToArray(), Rank = rank });
				}
			}

			return result.OrderByDescending(x => x.Rank).ToList();
		}
	}
}
