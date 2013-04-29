using System.IO;
using System.Text;
using MBI.Logic.Serialization._Impl;
using NUnit.Framework;

namespace MBI.Logic.Tests.SerializationTests
{
	[TestFixture]
	public class StreamHandlerTests
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
			var result = new StreamHandler().Read(stream);

			// Assert
			CollectionAssert.AreEqual(text, result);
		}

		[Test]
		public void writes_sample_text()
		{
			// Arrange
			var text = "text";
			var stream = new MemoryStream();

			// Act
			new StreamHandler().Write(text, stream);

			// Assert
			stream.Seek(0, SeekOrigin.Begin);

			using (var reader = new StreamReader(stream))
			{
				var result = reader.ReadToEnd();
				Assert.AreEqual(text, result);
			}
		}
	}
}
