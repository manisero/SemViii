namespace MBI.Logic.DNAAssemblance
{
	public interface IScaffoldValidator
	{
		int Validate(string[] contigs, PairedEndTag[] pairedEndTags);
	}
}
