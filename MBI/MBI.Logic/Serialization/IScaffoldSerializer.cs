using System.IO;

namespace MBI.Logic.Serialization
{
	public interface IScaffoldSerializer
	{
		void Serialize(Scaffold scaffold, Stream outputStream);
	}
}
