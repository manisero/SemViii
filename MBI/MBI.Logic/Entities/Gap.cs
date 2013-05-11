namespace MBI.Logic.Entities
{
	public class Gap : ScaffoldPiece
	{
		public int Length
		{
			get { return Content.Length; }
		}

		public Gap(int length) : base(new string(' ', length))
		{
		}
	}
}
