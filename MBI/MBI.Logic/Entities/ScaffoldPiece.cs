using System;

namespace MBI.Logic.Entities
{
	public abstract class ScaffoldPiece
	{
		public string Content { get; private set; }

		public int Length
		{
			get { return Content.Length; }
		}

		protected ScaffoldPiece(string content)
		{
			if (content == null)
			{
				throw new ArgumentNullException("content");
			}

			Content = content;
		}
	}
}
