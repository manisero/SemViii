using System.Collections.Generic;

namespace MBI.Logic
{
	public interface IDNAAssembler
	{
		IEnumerable<DNAAssembly> Assemble(IEnumerable<string> contigs, IEnumerable<PairedEndTag> pairedEndTags);
	}
}
