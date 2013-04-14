using System.Collections.Generic;

namespace MBI.Logic.DNAAssemblance
{
	public interface IAssemblyValidator
	{
		int Validate(IEnumerable<string> contigs, IEnumerable<PairedEndTag> pairedEndTags);
	}
}
