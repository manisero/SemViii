using System.Collections.Generic;

namespace MBI.Logic.Entities
{
	public class Scaffold
	{
		public IList<ScaffoldPiece> Pieces { get; set; }

		public int Rank { get; set; }

		public Scaffold()
		{
			Pieces = new List<ScaffoldPiece>();
		}
	}
}
