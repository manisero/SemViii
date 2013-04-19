using System.Collections.Generic;

namespace MBI.Logic.DNAAssemblance
{
	public interface IDNAAssembler
	{
		IList<Scaffold> Assemble(string[] contigs, PairedEndTag[] pairedEndTags);
	}
}
