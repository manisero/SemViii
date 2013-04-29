using System;
using System.IO;
using System.Text;
using NUnit.Framework;

namespace MBI.Logic.Tests.AssemblyParsingTests
{
	[TestFixture]
	public class StreamReaderTests
	{
		[Test]
		public void reads_sample_stream()
		{
			// Arrange
			var text = new[]
			           	{
			           		"line1",
			           		"line2",
			           		"line3"
			           	};

			var stream = new MemoryStream(ASCIIEncoding.Default.GetBytes(string.Join("\n", text)));
			
			// Act
			var result = new AssemblyParsing._Impl.StreamReader().Read(stream);

			// Assert
			CollectionAssert.AreEqual(text, result);
		}
	}
}
