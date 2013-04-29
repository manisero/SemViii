using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace MBI.Logic.Serialization._Impl
{
	public class AssemblyParser : IAssemblyParser
	{
		private readonly IStreamHandler _streamReader;

		public AssemblyParser(IStreamHandler streamReader)
		{
			_streamReader = streamReader;
		}

		public DNAAssembly Parse(Stream inputStream)
		{
			var inputText = _streamReader.Read(inputStream);

			ValiateInputText(inputText);

			int index;
			var contigs = new List<string>();

			for (index = 0; !string.IsNullOrEmpty(inputText[index]); index++)
			{
				contigs.Add(inputText[index]);
			}

			var pets = new List<PairedEndTag>();

			for (index++; index != inputText.Length; index++)
			{
				pets.Add(ParsePET(inputText[index]));
			}

			return new DNAAssembly { Contigs = contigs.ToArray(), PairedEndTags = pets.ToArray() };
		}

		private void ValiateInputText(string[] inputText)
		{
			if (inputText[0] == string.Empty)
			{
				throw new InvalidOperationException("Invalid input syntax. Contigs section not found");
			}

			if (!inputText.Contains(string.Empty))
			{
				throw new InvalidOperationException("Invalid input syntax. PET section is missing");
			}
		}

		private PairedEndTag ParsePET(string pet)
		{
			var petProperties = pet.Split(',');

			if (petProperties.Length != 3)
			{
				throw new InvalidOperationException(string.Format("Invalid PET syntax. Corrupted line: '{0}'", pet));
			}

			var result = new PairedEndTag();

			int petLength;

			if (!int.TryParse(petProperties[2], out petLength))
			{
				throw new InvalidOperationException(string.Format("Invalid PET length. Corrupted line: '{0}'", pet));
			}

			result.Beginning = petProperties[0];
			result.End = petProperties[1];
			result.Length = petLength;

			return result;
		}
	}
}