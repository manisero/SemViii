using System.IO;

namespace MBI.Logic.Serialization
{
	public interface IStreamReader
	{
		string[] Read(Stream stream);
	}
}
