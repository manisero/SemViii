using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IScaffoldBuilder
	{
		int Build(Contig[] contigs, PairedEndTag[] pairedEndTags);
	}
}
