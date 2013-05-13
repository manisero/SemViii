using System.Configuration;
using System.Globalization;
using MBI.Logic.Infrastructure;

namespace MBI.UI.Infrastructure
{
	public class SettingsProvider : ISettingsProvider
	{
		public double PartialMatchMinPercentage
		{
			get { return double.Parse(ConfigurationManager.AppSettings["PartialMatchMinPercentage"], CultureInfo.InvariantCulture.NumberFormat); }
		}
	}
}
