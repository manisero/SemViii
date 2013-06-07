using System.Collections.Generic;
using System.Threading;
using MBI.Logic.Entities;
using MBI.Logic.Infrastructure;

namespace MBI.Logic.DNAAssemblance
{
	public interface IDNAAssembler
	{
		IList<Scaffold> Assemble(Contig[] contigs, PairedEndTag[] pairedEndTags, ProgressIndication progressIndication, CancellationToken cancellationToken);
	}
}
