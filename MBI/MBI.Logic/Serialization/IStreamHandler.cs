using System.IO;

namespace MBI.Logic.Serialization
{
	public interface IStreamHandler
	{
		string[] Read(Stream stream);

		void Write(string text, Stream stream);
	}
}
