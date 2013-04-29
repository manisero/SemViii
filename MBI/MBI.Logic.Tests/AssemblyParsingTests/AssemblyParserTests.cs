using System;
using System.IO;
using MBI.Logic.AssemblyParsing;
using MBI.Logic.AssemblyParsing._Impl;
using NUnit.Framework;
using Rhino.Mocks;

namespace MBI.Logic.Tests
{
	[TestFixture]
	public class AssemblyParserTests
	{
		private IStreamReader _streamReaderMock;
		private AssemblyParser _assemblyParser;

		[SetUp]
		public void SetUp()
		{
			_streamReaderMock = MockRepository.GenerateMock<IStreamReader>();
			_assemblyParser = new AssemblyParser(_streamReaderMock);
		}

		[Test]
		public void parses_valid_input_stream()
		{
			// Arrange
			var inputStream = Stream.Null;
			var inputText = new[]
			                	{
			                		"contig1",
									"contig2",
			                		"",
			                		"pet1Beginning,pet1End,10",
			                		"pet2Beginning,pet2End,20"
			                	};

			_streamReaderMock.Expect(x => x.Read(inputStream)).Return(inputText);

			// Act
			var result = _assemblyParser.Parse(inputStream);

			// Assert
			Assert.IsNotNull(result);
			Assert.IsNotNull(result.Contigs);
			Assert.IsNotNull(result.PairedEndTags);

			Assert.AreEqual(2, result.Contigs.Length);
			Assert.AreEqual("contig1", result.Contigs[0]);
			Assert.AreEqual("contig2", result.Contigs[1]);

			Assert.AreEqual(2, result.PairedEndTags.Length);

			Assert.AreEqual("pet1Beginning", result.PairedEndTags[0].Beginning);
			Assert.AreEqual("pet1End", result.PairedEndTags[0].End);
			Assert.AreEqual(10, result.PairedEndTags[0].Length);

			Assert.AreEqual("pet2Beginning", result.PairedEndTags[1].Beginning);
			Assert.AreEqual("pet2End", result.PairedEndTags[1].End);
			Assert.AreEqual(20, result.PairedEndTags[1].Length);
		}

		[Test]
		[ExpectedException(typeof(InvalidOperationException))]
		public void rejects_input_without_contigs()
		{
			// Arrange
			var inputStream = Stream.Null;
			var inputText = new[]
			                	{
									"",
			                		"petBeginning,petEnd,10"
			                	};

			_streamReaderMock.Expect(x => x.Read(inputStream)).Return(inputText);

			// Act
			_assemblyParser.Parse(inputStream);
		}

		[Test]
		[ExpectedException(typeof(InvalidOperationException))]
		public void rejects_input_without_pets()
		{
			// Arrange
			var inputStream = Stream.Null;
			var inputText = new[]
			                	{
			                		"contig"
			                	};

			_streamReaderMock.Expect(x => x.Read(inputStream)).Return(inputText);

			// Act
			_assemblyParser.Parse(inputStream);
		}

		[Test]
		[ExpectedException(typeof(InvalidOperationException))]
		public void rejects_invalid_pet_format()
		{
			// Arrange
			var inputStream = Stream.Null;
			var inputText = new[]
			                	{
			                		"contig",
			                		"",
			                		"invalid_pet"
			                	};

			_streamReaderMock.Expect(x => x.Read(inputStream)).Return(inputText);

			// Act
			_assemblyParser.Parse(inputStream);
		}

		[Test]
		[ExpectedException(typeof(InvalidOperationException))]
		public void rejects_invalid_pet_length()
		{
			// Arrange
			var inputStream = Stream.Null;
			var inputText = new[]
			                	{
			                		"contig",
			                		"",
			                		"petBeginning,petEnd,invalidLength"
			                	};

			_streamReaderMock.Expect(x => x.Read(inputStream)).Return(inputText);

			// Act
			_assemblyParser.Parse(inputStream);
		}
	}
}
