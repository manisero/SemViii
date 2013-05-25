using System.Collections.Generic;
using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IContigsFilter
	{
		IEnumerable<IList<Contig>> Filter(IList<Contig> contigs, IEnumerable<PairedEndTag> pairedEndTags);
	}
}
