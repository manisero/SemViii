using System.IO;
using MBI.Logic.Entities;
using MBI.Logic.Serialization;
using MBI.Logic.Serialization._Impl;
using NUnit.Framework;
using Rhino.Mocks;

namespace MBI.Logic.Tests.SerializationTests
{
	[TestFixture]
	public class ScaffoldSerializerTests
	{
		private IStreamHandler _streamHandlerMock;
		private ScaffoldSerializer _scaffoldSerializer;

		[SetUp]
		public void SetUp()
		{
			_streamHandlerMock = MockRepository.GenerateMock<IStreamHandler>();
			_scaffoldSerializer = new ScaffoldSerializer(_streamHandlerMock);
		}

		[Test]
		public void serializes_sample_scaffold()
		{
			// Arrange
			var scaffold = new Scaffold { Contigs = new[] { "contig1", "contig2" }};
			var stream = new MemoryStream();

			_streamHandlerMock.Expect(x => x.Write(string.Join("\n", scaffold.Contigs), stream));

			// Act
			_scaffoldSerializer.Serialize(scaffold, stream);

			// Assert
			_streamHandlerMock.VerifyAllExpectations();
		}
	}
}
