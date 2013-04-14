using System.Collections.Generic;

namespace MBI.Logic.DNAAssemblance
{
	public interface IDNAAssembler
	{
		IList<DNAAssembly> Assemble(string[] contigs, PairedEndTag[] pairedEndTags);
	}
}
