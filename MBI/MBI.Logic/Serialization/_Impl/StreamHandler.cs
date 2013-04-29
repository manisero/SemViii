using System.Collections.Generic;
using System.IO;

namespace MBI.Logic.Serialization._Impl
{
	public class StreamHandler : IStreamHandler
	{
		public string[] Read(Stream stream)
		{
			var result = new List<string>();

			stream.Seek(0, SeekOrigin.Begin);
			var streamReader = new StreamReader(stream);

			while (!streamReader.EndOfStream)
			{
				result.Add(streamReader.ReadLine());
			}

			return result.ToArray();
		}

		public void Write(string text, Stream stream)
		{
			var writer = new StreamWriter(stream);

			writer.Write(text);
			writer.Flush();
		}
	}
}