using System.Collections.Generic;
using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IAssemblyFilter
	{
		IEnumerable<IList<Contig>> Filter(IEnumerable<IList<Contig>> contigsCombinations, IEnumerable<PairedEndTag> pairedEndTags);
	}
}
