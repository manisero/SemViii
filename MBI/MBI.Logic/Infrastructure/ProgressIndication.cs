using System;

namespace MBI.Logic.Infrastructure
{
	public class ProgressIndication
	{
		private Action _onChange;

		private double _progress;
		public double Progress
		{
			get { return _progress; }
			set
			{
				_progress = value;

				if (_onChange != null)
				{
					_onChange();
				}
			}
		}

		public ProgressIndication(Action onChange)
		{
			_onChange = onChange;
		}
	}
}
