using System.Collections.Generic;
using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IAssemblyFilter
	{
		IEnumerable<IList<Contig>> Filter(IList<IList<Contig>> contigsCombinations, IEnumerable<PairedEndTag> pairedEndTags);
	}
}
