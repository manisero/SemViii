using System.IO;

namespace MBI.Logic.AssemblyParser
{
	public interface IAssemblyParser
	{
		DNAAssembly Parse(Stream inputStream);
	}
}
