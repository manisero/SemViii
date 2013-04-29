using System.Collections.Generic;

namespace MBI.Logic.DNAAssemblance
{
	public interface IScaffoldValidator
	{
		int Validate(IEnumerable<string> contigs, IEnumerable<PairedEndTag> pairedEndTags);
	}
}
