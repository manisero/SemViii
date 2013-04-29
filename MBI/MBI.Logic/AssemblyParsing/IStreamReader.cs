using System.IO;

namespace MBI.Logic.AssemblyParsing
{
	public interface IStreamReader
	{
		string[] Read(Stream stream);
	}
}
