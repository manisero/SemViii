using MBI.Logic.Entities;

namespace MBI.Logic.DNAAssemblance
{
	public interface IScaffoldBuilder
	{
		Scaffold Build(Contig[] contigs, PairedEndTag[] pairedEndTags);
	}
}
