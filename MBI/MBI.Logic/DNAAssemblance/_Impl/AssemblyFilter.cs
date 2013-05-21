using System.Collections.Generic;
using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance._Impl
{
	public class AssemblyFilter : IAssemblyFilter
	{
		public IEnumerable<IList<Contig>> Filter(IEnumerable<IList<Contig>> contigsPermutations, IEnumerable<PairedEndTag> pairedEndTags)
		{
			return contigsPermutations;
		}
	}
}