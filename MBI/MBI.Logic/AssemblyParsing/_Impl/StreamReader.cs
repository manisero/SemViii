using System.Collections.Generic;
using System.IO;

namespace MBI.Logic.AssemblyParsing._Impl
{
	public class StreamReader : IStreamReader
	{
		public string[] Read(Stream stream)
		{
			var result = new List<string>();
			var streamReader = new System.IO.StreamReader(Stream.Null);

			while (!streamReader.EndOfStream)
			{
				result.Add(streamReader.ReadLine());
			}

			return result.ToArray();
		}
	}
}