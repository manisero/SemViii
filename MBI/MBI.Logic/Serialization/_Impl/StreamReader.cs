using System.Collections.Generic;
using System.IO;

namespace MBI.Logic.Serialization._Impl
{
	public class StreamReader : IStreamReader
	{
		public string[] Read(Stream stream)
		{
			stream.Seek(0, SeekOrigin.Begin);

			var result = new List<string>();
			var streamReader = new System.IO.StreamReader(stream);

			while (!streamReader.EndOfStream)
			{
				result.Add(streamReader.ReadLine());
			}

			return result.ToArray();
		}
	}
}