using System.IO;
using MBI.Logic.Entities;

namespace MBI.Logic.Serialization
{
	public interface IScaffoldSerializer
	{
		void Serialize(Scaffold scaffold, Stream outputStream);
	}
}
