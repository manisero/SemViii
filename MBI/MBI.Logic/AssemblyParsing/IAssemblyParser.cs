using System.IO;

namespace MBI.Logic.AssemblyParsing
{
	public interface IAssemblyParser
	{
		DNAAssembly Parse(Stream inputStream);
	}
}
