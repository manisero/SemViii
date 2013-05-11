using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IScaffoldValidator
	{
		int Validate(Contig[] contigs, PairedEndTag[] pairedEndTags);
	}
}
