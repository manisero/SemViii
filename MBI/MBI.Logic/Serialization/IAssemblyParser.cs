using System.IO;
using MBI.Logic.Entities;

namespace MBI.Logic.Serialization
{
	public interface IAssemblyParser
	{
		DNAAssembly Parse(Stream inputStream);
	}
}
