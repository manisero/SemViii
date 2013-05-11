namespace MBI.Logic.Entities
{
	public class Gap : ScaffoldPiece
	{
		public Gap(int length)
		{
			Content = new string(' ', length);
		}
	}
}
