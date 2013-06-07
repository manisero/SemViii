using System.Collections.Generic;
using System.Threading;
using MBI.Logic.Entities;
using System.Linq;
using MBI.Logic.Infrastructure;

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

		public IList<Scaffold> Assemble(Contig[] contigs, PairedEndTag[] pairedEndTags, ProgressIndication progressIndication, CancellationToken cancellationToken)
		{
			var result = new List<Scaffold>();
			var combinations = _contigsFilter.Filter(contigs, pairedEndTags, cancellationToken).ToList();

			for (int i = 0; i != combinations.Count; i++)
			{
				var scaffold = _scaffoldBuilder.Build(combinations[i].ToArray(), pairedEndTags, cancellationToken);

				if (scaffold.Rank > 0)
				{
					result.Add(scaffold);
				}

				progressIndication.Progress = (double)(i + 1) / combinations.Count;
				cancellationToken.ThrowIfCancellationRequested();
			}

			return result.OrderByDescending(x => x.Rank).ToList();
		}
	}
}
