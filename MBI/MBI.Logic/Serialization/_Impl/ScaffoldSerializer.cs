using System.IO;
using MBI.Logic.Entities;

namespace MBI.Logic.Serialization._Impl
{
	public class ScaffoldSerializer : IScaffoldSerializer
	{
		private readonly IStreamHandler _streamHandler;

		public ScaffoldSerializer(IStreamHandler streamHandler)
		{
			_streamHandler = streamHandler;
		}

		public void Serialize(Scaffold scaffold, Stream outputStream)
		{
			_streamHandler.Write(string.Join("\n", scaffold.Contigs), outputStream);
		}
	}
}