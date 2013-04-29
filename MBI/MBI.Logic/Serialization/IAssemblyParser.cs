using System.IO;

namespace MBI.Logic.Serialization
{
	public interface IAssemblyParser
	{
		DNAAssembly Parse(Stream inputStream);
	}
}
